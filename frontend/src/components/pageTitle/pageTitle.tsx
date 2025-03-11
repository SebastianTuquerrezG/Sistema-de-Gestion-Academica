import React from "react";
import styles from "../../assets/css/componets/pageTitle/pageTitle.module.css";

interface TitleProps {
    title: string;
}

export default function Title({ title }: TitleProps) {
    const words = title.split(" ");
    const firstWord = words.shift();
    const restOfTitle = words.join(" ");

    return (
        <h1 className={styles.title}>
            <span className={styles.firstWord}>{firstWord}</span> {restOfTitle}
        </h1>
    );
}