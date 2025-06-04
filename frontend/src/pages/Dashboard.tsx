import { Routes, Route } from "react-router-dom";
import DashboardLayout from "@/components/layouts/DashboardLayout";
import CreateRubric from "@/components/rubrics/CreateRubric";
import EditRubric from "@/components/rubrics/EditRubric";
import ConsultRubric from "@/components/rubrics/ConsultRubric";
import RubricDetail from "@/components/rubrics/RubricDetail";
import Evaluaciones from "@/views/Evaluaciones/Evaluation";
import Estadisticas from "@/views/Estadisticas/Estadisticas";
//import RepositorioRubricas from "@/components/rubrics/Repositorio";
import Repositorio from "@/components/rubrics/Repositorio";
import SubjectList from "@/views/Estudiante/subjectList.tsx";
import OtrosPeriodos from "@/views/Estudiante/OtrosPeriodos.tsx";
import RubricWrapper from "@/views/Estudiante/rubricListWrapper.tsx";
import Rubric from "@/views/Estudiante/rubric/rubric.tsx";
import { ProtectedRoute } from "@/routes/ProtectedRoute";
import NotFound from "@/pages/NotFound";

export default function Dashboard() {
    return (
        <DashboardLayout>
            <Routes>
                <Route
                    path="/"
                    element={
                        <ProtectedRoute allowedRoles={["ADMIN_ROLE", "COORDINATOR_ROLE", "TEACHER_ROLE"]}>
                            <ConsultRubric />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/rubricas"
                    element={
                        <ProtectedRoute allowedRoles={["ADMIN_ROLE", "COORDINATOR_ROLE", "TEACHER_ROLE"]}>
                            <ConsultRubric />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/rubricas/crear"
                    element={
                        <ProtectedRoute allowedRoles={["ADMIN_ROLE", "COORDINATOR_ROLE", "TEACHER_ROLE"]}>
                            <CreateRubric />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/rubricas/editar/:id"
                    element={
                        <ProtectedRoute allowedRoles={["ADMIN_ROLE", "COORDINATOR_ROLE", "TEACHER_ROLE"]}>
                            <EditRubric />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/rubricas/detalle/:id"
                    element={
                        <ProtectedRoute allowedRoles={["ADMIN_ROLE", "COORDINATOR_ROLE", "TEACHER_ROLE"]}>
                            <RubricDetail />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/rubricas/repositorio"
                    element={
                        <ProtectedRoute allowedRoles={["ADMIN_ROLE", "COORDINATOR_ROLE", "TEACHER_ROLE"]}>
                            <Repositorio />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/evaluaciones"
                    element={
                        <ProtectedRoute allowedRoles={["ADMIN_ROLE", "COORDINATOR_ROLE", "TEACHER_ROLE"]}>
                            <Evaluaciones />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/estadisticas"
                    element={
                        <ProtectedRoute allowedRoles={["ADMIN_ROLE", "COORDINATOR_ROLE", "TEACHER_ROLE"]}>
                            <Estadisticas />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/estudiante"
                    element={
                        <ProtectedRoute allowedRoles={["ADMIN_ROLE", "STUDENT_ROLE"]}>
                            <SubjectList />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/otrosperiodos/:idStudent"
                    element={
                        <ProtectedRoute allowedRoles={["ADMIN_ROLE", "STUDENT_ROLE"]}>
                            <OtrosPeriodos />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/rubric/:idStudent/:idSubject/:period"
                    element={
                        <ProtectedRoute allowedRoles={["ADMIN_ROLE", "STUDENT_ROLE"]}>
                            <RubricWrapper />
                        </ProtectedRoute>
                    }
                />
                <Route
                    path="/rubric/:idStudent/:idSubject/:period/:idRubric"
                    element={
                        <ProtectedRoute allowedRoles={["ADMIN_ROLE", "STUDENT_ROLE"]}>
                            <Rubric />
                        </ProtectedRoute>
                    }
                />
                <Route path="*" element={<NotFound />} />
            </Routes>
        </DashboardLayout>
    );
}