import React from "react";

interface StaticFieldProps {
  label: string;
  value: string;
}

const StaticField: React.FC<StaticFieldProps> = ({ label, value }) => {
  return (
    <div className="flex items-center gap-3 mb-4">
      <label className="font-bold">{label}:</label>
      <input
        type="text"
        value={value}
        readOnly
        autoComplete="off"
        className="px-3 py-2 text-lg font-bold rounded-lg border 
                   focus:outline-none bg-[#f0f0f0] text-[#444444] 
                   border-[#8f8f8f] w-full max-w-xs"
      />
    </div>
  );
};

export default StaticField;
