import { Routes, Route } from "react-router-dom";
import DashboardLayout from "@/components/layouts/DashboardLayout";
import CreateRubric from "@/components/rubrics/CreateRubric";
import EditRubric from "@/components/rubrics/EditRubric";
import ConsultRubric from "@/components/rubrics/ConsultRubric";
import RubricDetail from "@/components/rubrics/RubricDetail";
import Evaluaciones from "@/views/Evaluaciones/Evaluation";
import Estadisticas from "@/views/Estadisticas/Estadisticas";
//import RepositorioRubricas from "@/components/rubrics/Repositorio";
import  Repositorio from "@/components/rubrics/Repositorio";

export default function Dashboard() {
    return (
        <DashboardLayout>
            <Routes>
                <Route path="/" element={<ConsultRubric />} />
                <Route path="/rubricas" element={<ConsultRubric />} />
                <Route path="/rubricas/crear" element={<CreateRubric />} />
                <Route path="/rubricas/editar/:id" element={<EditRubric />} />
                <Route path="/rubricas/detalle/:id" element={<RubricDetail />} />
                <Route path="/rubricas/repositorio" element={<Repositorio />} />
                <Route path="/evaluaciones" element={<Evaluaciones />} />
                <Route path="/estadisticas" element={<Estadisticas />} />
            </Routes>
        </DashboardLayout>
    );
}