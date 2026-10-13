
import type { CSSProperties } from 'react';
import {styles} from "./header-navigation.style";


type HeaderNavigationProps = {
    title: string;
    subtitle: string;
};

export default function HeaderNavigation({
                                             title,
                                             subtitle,
                                         }: HeaderNavigationProps) {
    return (
        <header style={styles.container}>
            <div style={styles.content}>
                <h1 style={styles.title}>
                    {title}
                </h1>

                <p style={styles.subtitle}>
                    {subtitle}
                </p>
            </div>
        </header>
    );
}
