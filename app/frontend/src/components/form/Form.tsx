import * as React from "react";
import styles from "./Form.module.scss";

interface FormProps {
  children: React.ReactNode;
}

export default function Form({ children }: FormProps) {
  return <div className={styles.Form}>{children}</div>;
}
