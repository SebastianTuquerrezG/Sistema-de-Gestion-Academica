import Sidebar from "./sidebar";
import Header from "./header";
import React from "react";
import IconButton from "./../buttons/iconButton";

import "./layout.css";
import PageTitle from "../pageTitle/pageTitle.tsx";

const Layout = ({ children }: { children: React.ReactNode }) => {
  const handleClick = () => {
    alert("¡Botón clickeado!");
  };
  return (
    <div className="layout-container">
      <Sidebar />
      <div className="content-container">
        <Header />
        <div className="content">
          <p className="periodo-actual">Periodo actual: 2025-1</p>{" "}
          {/* Nuevo texto */}
          {/*   <PageTitle title="Buttons list" />*/}
          <IconButton onClick={handleClick} icon="add">
            Otros Periodos
          </IconButton>
        </div>
      </div>
    </div>
  );
};

export default Layout;
