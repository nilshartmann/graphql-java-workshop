import * as React from "react";
import styles from "./Form.module.scss";

interface SelectOption {
  value: string;
  label: string;
}

interface SelectProps {
  label: string;
  value: string;
  options: SelectOption[];
  onNewValue(newValue: string): void;
}

export default function Select({ label, value, options, onNewValue }: SelectProps) {
  function handleSelectChange(e: React.ChangeEvent<HTMLSelectElement>) {
    const value = e.currentTarget.value;
    if ("UNSELECTED" === value) {
      onNewValue("");
    } else {
      onNewValue(value);
    }
  }

  return (
    <div className={styles.Select}>
      <label>{label}</label>
      <select onChange={handleSelectChange} value={value === "" ? "UNSELECTED" : value}>
        <option value={"UNSELECTED"} />
        {options.map(option => (
          <option key={option.value} value={option.value}>
            {option.label}
          </option>
        ))}
      </select>
    </div>
  );
}
