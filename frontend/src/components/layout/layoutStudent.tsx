import SidebarStudent from "./sidebarStudent.tsx";
import HeaderStudent from "./headerStudent.tsx";
import React  from "react";


import "./layoutStudent.css";
//import PageTitle from "../pageTitle/pageTitle.tsx";

const LayoutStudent = ({children}:{children:React.ReactNode}) => {
  
  return (
      <div className="layout-container">
          <SidebarStudent />
        <div className="content-container">
            <HeaderStudent />
          <div className="content">
            <main>
                {children}
            </main>
          </div>
        </div>
      </div>
  );
};

export default LayoutStudent;
