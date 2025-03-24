import React from "react";
import PageTitle from "../../components/pageTitle/pageTitle";
import RubricaInfo from "./rubricaInfo";
import EvaluationTable from "./evaluationTable";
import ActionButtons from "../../components/utils/actionButtons";
import "../../assets/css/evaluaciones.css";

const Evaluaciones: React.FC = () => {
  return (
    <>
      <div className="header-row">
        <PageTitle title="Evaluaciones" />
        <ActionButtons />
      </div>
      <RubricaInfo />
      <EvaluationTable />
    </>
  );
};

export default Evaluaciones;
