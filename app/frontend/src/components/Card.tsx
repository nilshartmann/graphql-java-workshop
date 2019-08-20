import * as React from "react";
import styles from "./Card.module.scss";
import Button from "./Button";
import ButtonBar from "./ButtonBar";

type CardProps = {
  children: React.ReactNode;
};
export default function Card({ children }: CardProps) {
  return <div className={styles.Card}>{children}</div>;
}

type InfoCardAction = {
  label: string;
  onExecute(): void;
};

type InfoCardProps = {
  label: React.ReactNode;
  title: React.ReactNode;

  actions?: InfoCardAction[];
};

export function InfoCard({ title, label, actions }: InfoCardProps) {
  const cardActions = actions ? (
    <ButtonBar left>
      {actions.map((a, ix) => (
        <Button key={ix} tertiary onClick={a.onExecute}>
          {a.label}
        </Button>
      ))}
    </ButtonBar>
  ) : null;

  return (
    <Card>
      <div className={styles.CardLabel}>{label}</div>
      <div className={styles.CardTitle}>{title}</div>
      {cardActions}
    </Card>
  );
}
