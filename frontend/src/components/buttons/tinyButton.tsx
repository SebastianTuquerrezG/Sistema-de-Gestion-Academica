// src/components/buttons/tinyButton.tsx
import React from 'react';
import '../../assets/css/componets/buttons/tinyButton.css';

interface TinyButtonProps {
    onClick: () => void;
    icon: string;
}

const TinyButton: React.FC<TinyButtonProps> = ({ onClick, icon }) => {
    return (
        <button className="tiny-button" onClick={onClick}>
            <span className="material-symbols-outlined">{icon}</span>
        </button>
    );
};

export default TinyButton;