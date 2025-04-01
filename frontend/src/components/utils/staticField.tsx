import React from "react";

interface StaticFieldProps {
    label: string;
    value: string;
}

const StaticField: React.FC<StaticFieldProps> = ({ label, value }) => {
    return (
        <div className="input-label-container">
            <label className="input-label">{label}:</label>
            <div className="input-value">{value}</div>
        </div>
    );
};

export default StaticField;
