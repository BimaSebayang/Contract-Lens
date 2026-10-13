import { Smartphone } from 'lucide-react';

export default function MobileOnlyNotice() {
    return (
        <div
            style={{
                minHeight: '100vh',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                padding: 24,
                boxSizing: 'border-box',
                backgroundColor: '#F8FAFC',
                fontFamily: 'Outfit, sans-serif',
            }}
        >
            <div
                style={{
                    width: '100%',
                    maxWidth: 380,
                    textAlign: 'center',
                    padding: 28,
                    borderRadius: 20,
                    backgroundColor: '#FFFFFF',
                    border: '1px solid #E7EDF5',
                }}
            >
                <Smartphone
                    size={42}
                    color="#1268F3"
                    style={{ marginBottom: 16 }}
                />

                <h2 style={{ color: '#07152F', margin: '0 0 12px' }}>
                    Fitur Khusus Mobile
                </h2>

                <p style={{ color: '#64748B', lineHeight: 1.7, margin: 0 }}>
                    Fitur ini hanya tersedia di perangkat mobile.
                    **Buka CLAwfo melalui HP Anda** untuk menikmati fitur ini dan melanjutkan aktivitas Anda.
                </p>
            </div>
        </div>
    );
}