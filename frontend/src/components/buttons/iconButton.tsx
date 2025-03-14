// src/components/buttons/iconButton.tsx
import React from 'react';
import './iconButton.css';

interface IconButtonProps {
    onClick: () => void;
    icon: string;
    children: React.ReactNode;
}

const IconButton: React.FC<IconButtonProps> = ({ onClick, icon, children }) => {
    return (
        <button className="icon-button" onClick={onClick}>
            <span className="material-symbols-outlined">{icon}</span>
            <span className="icon-button-text">{children}</span>
        </button>
    );
};

export default IconButton;