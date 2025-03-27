import { Routes, Route } from "react-router-dom";
import DashboardLayout from "@/components/layouts/DashboardLayout";
import CreateRubric from "@/components/rubrics/CreateRubric";
import EditRubric from "@/components/rubrics/EditRubric";
import ConsultRubric from "@/components/rubrics/ConsultRubric";

export default function Dashboard() {
    return (
        <DashboardLayout>
            <Routes>
                <Route index path="/rubricas" element={<ConsultRubric />} />
                <Route path="/" element={<ConsultRubric />} />
                <Route path="/rubricas/crear" element={<CreateRubric />} />
                <Route path="/rubricas/editar" element={<EditRubric />} />
            </Routes>
        </DashboardLayout>
    );
};