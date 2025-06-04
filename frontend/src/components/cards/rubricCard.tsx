import React from "react";

import "./rubricCard.css";

interface Props {
    name: string;
    date: string;
    onClick?: () => void;
}


export const RubricCard = ({ name, date, onClick }: Props) => {

    return (
        <div className="rubric-card" onClick={onClick}>
            <div className="rubric-icon">
                <span className="material-symbols-outlined">view_list</span>
            </div>
            <div>
                <h3 className="rubric-title">{name}</h3>
                <p className="rubric-date">{date}</p>
            </div>
        </div>
    );
}