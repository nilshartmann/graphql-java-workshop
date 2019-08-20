import * as React from "react";
import styles from "./ButtonBar.module.scss";

type ButtonBarProps = {
  left?: boolean;
  children: React.ReactNode;
};

export default function ButtonBar({ left, children }: ButtonBarProps) {
  const className = left ? `${styles.ButtonBar} ${styles.left}` : styles.ButtonBar;
  return <div className={className}>{children}</div>;
}
