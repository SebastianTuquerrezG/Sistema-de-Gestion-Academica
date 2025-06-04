
import { Routes, Route } from "react-router-dom";
import { ProtectedRoute } from "@/routes/ProtectedRoute";
import Dashboard from "@/pages/Dashboard";
import Login from "@/pages/Login";
import AccessDenied from "@/pages/AccessDenied";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/login" element={<Login />} />
      <Route path="/acceso-denegado" element={<AccessDenied />} />
      <Route
        path="/*"
        element={
          <ProtectedRoute allowedRoles={["ADMIN_ROLE", "COORDINATOR_ROLE", "TEACHER_ROLE", "STUDENT_ROLE"]}>
            <Dashboard />
          </ProtectedRoute>
        }
      />
    </Routes>
  );
}
export default App;