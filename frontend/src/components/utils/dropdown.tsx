import { Menu, MenuButton, MenuItem, MenuItems } from "@headlessui/react";
import { ChevronDownIcon } from "@heroicons/react/20/solid";
import React from "react";
import "../../views/Evaluaciones/EvaluacionesCSS/rubricaInfo.css"; // Importamos los estilos

interface DropdownProps {
  label: string;
  options: string[];
}

const Dropdown: React.FC<DropdownProps> = ({ label, options }) => {
  return (
      <div className="dropdown-container">
          <label className="dropdown-label">{label}:</label>
          <select className="dropdown">
              {options.map((option, index) => (
                  <option key={index} value={option}>{option}</option>
              ))}
          </select>
      </div>
  );
};

export default Dropdown;
