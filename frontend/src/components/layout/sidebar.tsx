import React from "react";
import "./sidebar.css"; // Importa tu CSS si es necesario

const Sidebar: React.FC = () => {
    return (
        <aside className="sidebar open">
            {/* Imagen */}
            <div className="sidebar-image">
                <img src="path/to/your/image.jpg" alt="Logo" />
            </div>
            {/* Menú */}
            <ul className="menu">
                <li className="menu-item">
                    <span className="material-symbols-outlined">home</span>
                    <span className="title5">Inicio</span>
                </li>
                <li className="menu-item">
                    <span className="material-symbols-outlined">settings</span>
                    <span className="title5">Configuración</span>
                </li>
                <li className="menu-item">
                    <span className="material-symbols-outlined">person</span>
                    <span className="title5">Perfil</span>
                </li>
            </ul>
        </aside>
    );
};

export default Sidebar;