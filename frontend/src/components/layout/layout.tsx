import Sidebar from "./sidebar";
import Header from "./header";
import React from "react";
import "../../assets/css/componets/layout/layout.css";

const Layout = ({ children }: { children: React.ReactNode }) => {
  return (
    <div className="layout-container">
      <Sidebar />
      <div className="content-container">
        <Header />
        <div className="content">
          <div className="page-wrapper">
            {children}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Layout;
