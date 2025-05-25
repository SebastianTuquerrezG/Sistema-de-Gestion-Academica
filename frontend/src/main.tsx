import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import OtrosPeriodos from "./components/pageTitle/OtrosPeriodos.tsx";
import SubjectList from "./views/subjectList.tsx";
import RubricWrapper from "./views/rubricListWrapper.tsx";
import Rubric from "./views/rubric/rubric.tsx";

import "./assets/css/global.css";


const App: React.FC = () => (


  <BrowserRouter>
    <Routes>
      <Route path="/" element={<SubjectList />} />
      <Route path="/otrosperiodos" element={<OtrosPeriodos />} />
        <Route path="/rubric/:idStudent/:idSubject/:period" element={<RubricWrapper />} />
        <Route path="/rubric/:idStudent/:idSubject/:period/:idRubric" element={<Rubric />} />
    </Routes>
  </BrowserRouter>
);

const rootElement = document.getElementById("app");

if (rootElement) {
  const root = ReactDOM.createRoot(rootElement);
  root.render(<App />);
} else {
  console.error("No se encontró el elemento con id 'app'.");
}
