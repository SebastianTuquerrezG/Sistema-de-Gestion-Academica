import { Menu, MenuButton, MenuItem, MenuItems } from "@headlessui/react";
import { ChevronDownIcon } from "@heroicons/react/20/solid";
import React from "react";
import "../../views/Evaluaciones/EvaluacionesCSS/rubricaInfo.css"; // Importamos los estilos

interface DropdownProps {
  label: string;
  options: { id: number; name: string }[];
  selected: string;
  onSelect: (id: number) => void;
}

const Dropdown: React.FC<DropdownProps> = ({ label, options, selected, onSelect }) => {
  return (
    <div className="info-block">
      <label>{label}:</label>
      <Menu as="div" className="dropdown-container">
        <div>
          <MenuButton className="dropdown-button">
            {selected}
            <ChevronDownIcon className="w-5 h-5 dropdown-icon" />
          </MenuButton>
        </div>

        <MenuItems className="absolute left-0 z-10 mt-2 w-half max-w-xs origin-top-left rounded-lg bg-white border border-gray-300 shadow-lg focus:outline-none">
          {options.map((option) => (
            <MenuItem key={option.id}>
              {({ active }: { active: boolean }) => (
                <button
                  onClick={() => onSelect(option.id)}
                  className={`block w-full text-left px-4 py-2 text-lg ${
                    active ? "bg-gray-200 text-gray-900" : "text-gray-700"
                  }`}
                >
                  {option.name}
                </button>
              )}
            </MenuItem>
          ))}
        </MenuItems>
      </Menu>
    </div>
  );
};

export default Dropdown;
