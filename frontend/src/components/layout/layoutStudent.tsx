import React from "react";


import "./layoutStudent.css";
//import PageTitle from "../pageTitle/pageTitle.tsx";

const LayoutStudent = ({ children }: { children: React.ReactNode }) => {

  return (

    <div className="content">
      <main>
        {children}
      </main>
    </div>

  );
};

export default LayoutStudent;
