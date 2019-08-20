import * as React from "react";
import styles from "./NavButton.module.scss";

import Octicon, { ChevronRight } from "@primer/octicons-react";

interface NavButtonProps {
  onClick: React.MouseEventHandler<HTMLElement>;
}

export default function NavButton({ onClick }: NavButtonProps) {
  return (
    <button className={styles.NavButton} onClick={onClick}>
      <Octicon icon={ChevronRight} />
    </button>
  );
}
