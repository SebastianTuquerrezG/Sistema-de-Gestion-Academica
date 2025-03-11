import React from 'react';
import styles from '../../assets/css/componets/notifications/notification.module.css';


interface NotificationProps {
    type: 'success' | 'info' | 'error' | 'security';
    title: string;
    message: string;
    onClose: () => void;
}

const Notification: React.FC<NotificationProps> = ({ type, title, message, onClose }) => {
    return (
        <div className={`${styles.notification} ${styles[type]}`}>
            <div className={styles.icon}></div>
            <div className={styles.content}>
                <strong>{title}</strong>
                <p>{message}</p>
            </div>
            <button className={styles.close} onClick={onClose}>x</button>
        </div>
    );
};

export default Notification;
