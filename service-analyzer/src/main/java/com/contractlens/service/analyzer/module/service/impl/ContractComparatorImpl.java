package com.contractlens.service.analyzer.module.service.impl;

import com.contractlens.common.dto.ContractCompareResult;
import com.contractlens.common.dto.ContractDifference;
import com.contractlens.common.dto.ContractSnapshot;
import com.contractlens.common.dto.SchemaField;
import com.contractlens.common.enums.DifferenceType;
import com.contractlens.service.analyzer.module.service.ContractComparator;
import com.contractlens.service.analyzer.module.service.JsonReaderSvc;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContractComparatorImpl implements ContractComparator {

    private final JsonReaderSvc jsonReaderSvc;

    @Override
    public ContractCompareResult compare(ContractSnapshot baseline, ContractSnapshot runtime) {
        ContractCompareResult result = new ContractCompareResult();

        compareRemoved(result, baseline, runtime);

        compareAdded(result, baseline, runtime);

        compareType(result, baseline, runtime);

        return result;
    }

    @Override
    public ContractCompareResult compare(String baseline, String runtime) {
        return compare(jsonReaderSvc.readContract(baseline),jsonReaderSvc.readContract(runtime));
    }

    private void compareType(
            ContractCompareResult result,
            ContractSnapshot baseline,
            ContractSnapshot runtime
    ) {

        for (SchemaField baselineField : baseline.getFields().values()) {

            if (!runtime.contains(baselineField.getPath())) {
                continue;
            }

            SchemaField runtimeField =
                    runtime.getField(baselineField.getPath());

            if (baselineField.getDataType()
                    != runtimeField.getDataType()) {

                result.setMatched(false);

                result.getDifferences().add(
                        createDifference(
                                DifferenceType.TYPE_CHANGED,
                                baselineField,
                                runtimeField
                        )
                );

            }

        }

    }

    private void compareAdded(
            ContractCompareResult result,
            ContractSnapshot baseline,
            ContractSnapshot runtime
    ) {

        for (SchemaField field : runtime.getFields().values()) {

            if (!baseline.contains(field.getPath())) {

                result.setMatched(false);

                result.getDifferences().add(
                        createDifference(
                                DifferenceType.ADDED,
                                null,
                                field
                        )
                );

            }

        }

    }

    private void compareRemoved(
            ContractCompareResult result,
            ContractSnapshot baseline,
            ContractSnapshot runtime
    ) {

        for (SchemaField field : baseline.getFields().values()) {

            if (!runtime.contains(field.getPath())) {

                result.setMatched(false);

                result.getDifferences().add(
                        createDifference(
                                DifferenceType.REMOVED,
                                field,
                                null
                        )
                );

            }

        }

    }


    private ContractDifference createDifference(
            DifferenceType type,
            SchemaField expected,
            SchemaField actual
    ) {

        ContractDifference difference =
                new ContractDifference();

        difference.setType(type);

        difference.setPath(
                expected != null
                        ? expected.getPath()
                        : actual.getPath()
        );

        difference.setExpected(
                expected != null
                        ? expected.getDataType()
                        : null
        );

        difference.setActual(
                actual != null
                        ? actual.getDataType()
                        : null
        );

        return difference;

    }

}
