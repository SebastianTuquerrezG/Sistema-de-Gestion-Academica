import Sidebar from "./sidebar";
import Header from "./header";
import React from "react";

import "../../assets/css/componets/layout/layout.css";
import PageTitle from "../pageTitle/pageTitle.tsx";

const Layout = ({ children }: { children: React.ReactNode }) => {
    return (
        <div className="layout-container">
            <Sidebar />
            <div className="content-container">
                <Header />
                <div className="content">
                    <PageTitle title="Buttons list" />
                    <main className="content">{children}</main>
                </div>
            </div>
        </div>
    );
};

export default Layout;
