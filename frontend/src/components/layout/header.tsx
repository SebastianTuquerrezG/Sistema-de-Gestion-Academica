import React from "react";
import "../../assets/css/componets/layout/header.css"; // Importa tu CSS si es necesario

const Header: React.FC = () => {
    return (
        <header className="header">
            <h1 className="title4">Universidad del Cauca</h1>
            <button className="header-button">
                <span className="material-symbols-outlined">account_circle</span>
                <div className="user-info">
                    <span className="title5">Nombre completo de usuario</span>
                    <span className="text3">Rol de usuario</span>
                </div>
            </button>
        </header>
    );
};

export default Header;