import React, { useState, useEffect } from "react";
import "../../views/Evaluaciones/EvaluacionesCSS/rubricaInfo.css";

interface DropdownProps {
  label: string;
  options: string[];
  onSelect?: (value: string) => void;
  value?: string;
}

const Dropdown: React.FC<DropdownProps> = ({ label, options, onSelect, value }) => {
  const [query, setQuery] = useState("");
  const [showOptions, setShowOptions] = useState(false);

  // Sincroniza el valor externo con el input
  useEffect(() => {
    if (typeof value === "string") {
      setQuery(value);
    }
  }, [value]);

  const filteredOptions = options.filter(
    (option) =>
      typeof option === "string" &&
      option.toLowerCase().includes(query.toLowerCase())
  );

  const handleSelect = (val: string) => {
    setQuery(val);
    setShowOptions(false);
    if (onSelect) onSelect(val);
  };

  return (
    <div className="dropdown-container">
      <label className="dropdown-label">{label}:</label>
      <input
        type="text"
        value={typeof value === "string" ? value : query}
        onChange={(e) => {
          const val = e.target.value;
          setQuery(val);
          setShowOptions(true);
          if (val.trim() === "" && onSelect) {
            onSelect("");
          }
        }}
        onFocus={() => setShowOptions(true)}
        onBlur={() => setTimeout(() => setShowOptions(false), 100)}
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
