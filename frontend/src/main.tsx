import React from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import IconButton from "./components/buttons/iconButton.tsx";
import "./assets/css/global.css";

import Layout from "./components/layout/layout.tsx";
import Evaluaciones from "./views/Evaluaciones/Evaluaciones.tsx"; // Importa el módulo de Evaluaciones

const App: React.FC = () => {
    return (
        <Router>
            <Layout>
                <Routes>
                    {/* Ruta principal (puede ser otra si lo necesitas) */}
                    <Route path="/" element={<h1>Bienvenido al sistema</h1>} />

                    {/* Ruta para el módulo de Evaluaciones */}
                    <Route path="/evaluaciones" element={<Evaluaciones />} />
                </Routes>
            </Layout>
        </Router>
    );
};

const rootElement = document.getElementById("app");

if (rootElement) {
    const root = ReactDOM.createRoot(rootElement);
    root.render(<App />);
} else {
    console.error("No se encontró el elemento con id 'app'.");
}
