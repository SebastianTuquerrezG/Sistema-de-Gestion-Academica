import React from "react";
import PageTitle from "../../components/pageTitle/pageTitle";
import RubricaInfo from "./rubricaInfo";
import EvaluationTable from "./evaluationTable";

const Evaluaciones: React.FC = () => {
    return (
      <div className="content">
        <PageTitle title="Evaluaciones" />
        <RubricaInfo />
        <EvaluationTable />
      </div>
    );
  };
  
  export default Evaluaciones;
