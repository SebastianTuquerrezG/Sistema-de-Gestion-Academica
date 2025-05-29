import React from "react";
import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.tsx'
import { BrowserRouter } from "react-router-dom";
// import './index.css'
// import ReactDOM from "react-dom/client";
//import { BrowserRouter, Routes, Route } from "react-router-dom";
// import OtrosPeriodos from "./components/pageTitle/OtrosPeriodos.tsx";
// import SubjectList from "./views/Estudiante/subjectList.ts";
// import RubricWrapper from "./views/Estudiante/rubricListWrapper.tsx";
// import Rubric from "./views/Estudiante/rubric/rubric.tsx";

//import "./assets/css/global.css";

createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <BrowserRouter>
            <App />
        </BrowserRouter>
    </StrictMode>,
)
