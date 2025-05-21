import React from "react";
import "./subjectCard.css";

interface Props {
    title: string;
    period: string;
    professor: string;

}

export const SubjectHeader = ({ title, period, professor }: Props) => (
    <section className="subject-header">
        <span className="subject-title">{title}</span>
        <div className="subject-period">
            <span>{period}</span>
            <span>{professor}</span>
        </div>
    </section>
);