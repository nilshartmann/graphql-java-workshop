import * as React from "react";
import styles from "./Button.module.scss";
import Octicon, { Icon } from "@primer/octicons-react";
import { MouseEventHandler } from "react";

interface ButtonProps {
  children: React.ReactNode;
  secondary?: boolean;
  tertiary?: boolean;
  icon?: Icon;
  onClick?: MouseEventHandler<HTMLButtonElement>;
  disabled?: boolean;
}

export default function Button({ children, icon, onClick, secondary, tertiary, disabled }: ButtonProps) {
  const buttonClassName = secondary
    ? `${styles.Button} ${styles.secondary}`
    : tertiary
    ? `${styles.Button} ${styles.tertiary}`
    : styles.Button;

  return (
    <button className={buttonClassName} onClick={onClick} disabled={disabled}>
      {children}
      {icon && (
        <span className={styles.Icon}>
          <Octicon icon={icon} />
        </span>
      )}
    </button>
  );
}
