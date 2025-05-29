import React from "react";
import { Routes, Route } from "react-router-dom";
import { ProtectedRoute } from "./routes/ProtectedRoute";
import Dashboard from "./pages/Dashboard";
import Login from "./pages/Login";
import SubjectList from "./views/Estudiante/subjectList.tsx";
import OtrosPeriodos from "./views/Estudiante/OtrosPeriodos.tsx";
import RubricWrapper from "./views/Estudiante/rubricListWrapper.tsx";
import Rubric from "./views/Estudiante/rubric/rubric.tsx";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/login" element={<Login />} />
      <Route
        path="/*"
        element={
          <ProtectedRoute>
            <Dashboard />
          </ProtectedRoute>
        }
      />
        <Route path="/" element={<SubjectList />} />
        <Route path="/otrosperiodos" element={<OtrosPeriodos />} />
        <Route path="/rubric/:idStudent/:idSubject/:period" element={<RubricWrapper />} />
        <Route path="/rubric/:idStudent/:idSubject/:period/:idRubric" element={<Rubric />} />
    </Routes>
  );
}
export default App;