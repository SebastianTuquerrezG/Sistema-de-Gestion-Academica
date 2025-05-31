import React, { useEffect, useState } from 'react';
import styles from './notification.module.css';

interface NotificationProps {
    type: 'success' | 'info' | 'error' | 'security';
    title: string;
    message: string;
    onClose: () => void;
    autoClose?: boolean;
    duration?: number;
}

const Notification: React.FC<NotificationProps> = ({
    type,
    title,
    message,
    onClose,
    autoClose = true,
    duration = 3500,
}) => {
    const [isClosing, setIsClosing] = useState(false);

    useEffect(() => {
        if (autoClose) {
            const timeout = setTimeout(() => {
                setIsClosing(true);
                setTimeout(onClose, 300);
            }, duration);
            return () => clearTimeout(timeout);
        }
    }, [autoClose, duration, onClose]);

    const handleClose = () => {
        setIsClosing(true);
        setTimeout(onClose, 300);
    };

    return (
        <div className={`${styles.notification} ${styles[type]} ${isClosing ? styles.fadeOut : ''}`}>
            <div className={styles.icon}></div>
            <div className={styles.content}>
                <strong>{title}</strong>
                <p>{message}</p>
            </div>
            <button className={styles.close} onClick={handleClose} aria-label="Close">
                &times;
            </button>
        </div>
    );
};

export default Notification;