import { Routes, Route, useParams } from "react-router-dom";
import DashboardLayout from "@/components/layouts/DashboardLayout";
import CreateRubric from "@/components/rubrics/CreateRubric";
import EditRubric from "@/components/rubrics/EditRubric";
import ConsultRubric from "@/components/rubrics/ConsultRubric";

function EditRubricWrapper() {
    const params = useParams<{id:string} >();
    return <EditRubric params={params as {id:string}} />;
}
export default function Dashboard() {
    return (
        <DashboardLayout>
            <Routes>
                <Route index path="/rubricas" element={<ConsultRubric />} />
                <Route path="/rubricas/crear" element={<CreateRubric />} />
                <Route path="/rubricas/editar/:id" element={<EditRubricWrapper />} />
            </Routes>
        </DashboardLayout>
    );
};