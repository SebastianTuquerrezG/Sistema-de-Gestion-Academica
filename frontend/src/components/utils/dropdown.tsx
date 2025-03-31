import React, { useState } from "react";
import "../../views/Evaluaciones/EvaluacionesCSS/rubricaInfo.css";

interface DropdownProps {
  label: string;
  options: string[];
  onSelect?: (value: string) => void;
}

const Dropdown: React.FC<DropdownProps> = ({ label, options, onSelect }) => {
  const [query, setQuery] = useState("");
  const [showOptions, setShowOptions] = useState(false);

  const filteredOptions = options.filter((option) =>
    option.toLowerCase().includes(query.toLowerCase())
  );

  const handleSelect = (value: string) => {
    setQuery(value);
    setShowOptions(false);
    if (onSelect) onSelect(value);
  };

  return (
    <div className="dropdown-container">
      <label className="dropdown-label">{label}:</label>
      <input
        type="text"
        value={query}
        onChange={(e) => {
          setQuery(e.target.value);
          setShowOptions(true);
        }}
        onFocus={() => setShowOptions(true)}
        onBlur={() => setTimeout(() => setShowOptions(false), 100)} // evita cierre instantáneo al hacer clic
        className="dropdown"
        placeholder={`Seleccione ${label.toLowerCase()}`}
      />
      {showOptions && (
        <ul className="dropdown-options">
          {filteredOptions.length > 0 ? (
            filteredOptions.map((option, index) => (
              <li key={index} onMouseDown={() => handleSelect(option)}>
                {option}
              </li>
            ))
          ) : (
            <li className="dropdown-option-empty">No hay coincidencias</li>
          )}
        </ul>
      )}
    </div>
  );
};

export default Dropdown;
