// src/components/buttons/primaryButton.tsx
import React from 'react';
import '../../assets/css/componets/buttons/primaryButton.css';

interface PrimaryButtonProps {
    onClick: () => void;
    children: React.ReactNode;
}

const primaryButton: React.FC<PrimaryButtonProps> = ({ onClick, children }) => {
    return (
        <button className="primary-button" onClick={onClick}>
            {children}
        </button>
    );
};

export default primaryButton;
