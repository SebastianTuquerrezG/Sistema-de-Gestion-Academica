import React, {useEffect} from "react";
import { SubjectHeader } from "../components/cards/subjectCard.tsx";
import { RubricCard } from "../components/cards/rubricCard.tsx";
import { getRubrics, getSubjectHeader } from "../services/rubricService.ts";
//import { useNavigate } from "react-router-dom";
import Layout from "../components/layout/layout.tsx";
import "./rubricList.css"

type RubricListProps = {
    idStudent: number;
    idSubject: number;
    period: string;

};

const RubricList: React.FC<RubricListProps> = ({ idStudent, idSubject, period }) => {

    type Rubric = {
        nameRubric: string;
        created_at: string;
    };

    type SubjectHeader = {
        id: number;
        nameSubject: string;
        period: string;
        nameTeacher: string;
    };

    const [rubrics, setRubrics] = React.useState<Rubric[]>([]);
    const [subjectHeader, setSubjectHeader] = React.useState<SubjectHeader>();
    //  const navigate = useNavigate();


    useEffect(() => {
        const fetchData = async () => {
            try {
                const rubricData = await getRubrics(idStudent, idSubject, period);
                setRubrics(rubricData);

                const subjectData = await getSubjectHeader(idStudent,period, idSubject );
                setSubjectHeader(subjectData);
            } catch (error) {
                console.error("Error en la carga de datos:", error);
            }
        };
        fetchData();
    }, [idStudent, idSubject, period]);

    return (
        <Layout>
            <div className="subject-header">
                {subjectHeader && (
                    <SubjectHeader
                        title={subjectHeader.nameSubject}
                        period={subjectHeader.period}
                        professor={subjectHeader.nameTeacher}
                    />
                )}
            </div>
            <div className="rubric-list">
                {rubrics.map((rubric: any) => (
                    <RubricCard
                        key={rubric.nameRubric}
                        name={rubric.nameRubric}
                        date={rubric.created_at}
                        onClick={() => alert("¡Haz hecho clic en la tarjeta!")}
                    />
                ))}
            </div>
        </Layout>
    );
};

export default RubricList;
