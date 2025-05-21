import React from "react";
import "./sidebar.css";
import { useNavigate} from "react-router-dom"; // Importa tu CSS si es necesario

const Sidebar: React.FC = () => {
    const navigate = useNavigate();
    //const location = useLocation();

    const handleClick = () => {
        navigate("/");
        // alert("No hay otros periodos disponibles");
    };

    return (
    <aside className="sidebar open">
      {/* Imagen */}
      <div className="sidebar-image">
        <img src="/logo.svg" alt="Logo" />
      </div>
      {/* Menú */}
      <ul className="menu">
        {/*
        <li className="menu-item">
          <span className="material-symbols-outlined">home</span>
          <span className="title5">Inicio tocando</span>
        </li>
        <li className="menu-item">
          <span className="material-symbols-outlined">settings</span>
          <span className="title5">Configuración</span>
        </li>
        <li className="menu-item">
          <span className="material-symbols-outlined">person</span>
          <span className="title5">Perfil</span>
        </li> */}
        <li className="menu-item">
          <span className="material-symbols-outlined">school</span>
          <span className="title5" onClick={handleClick}>Estudiante</span>
        </li>
      </ul>
    </aside>
  );
};

export default Sidebar;
