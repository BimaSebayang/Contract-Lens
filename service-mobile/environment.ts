import {
    environment as development,
} from './environment.development';

import {
    environment as production,
} from './environment.production';


const isProduction =
    process.env.NODE_ENV === 'production';


export const environment =
    isProduction
        ? production
        : development;