import { z } from "zod";

export const userSchema = z.object({
    username: z
        .string()
        .min(1, "El correo o usuario es obligatorio")
        .refine(
            (value) =>
                value.endsWith("@unicauca.edu.co") ||
                /^[a-zA-Z0-9._-]+$/.test(value), // usuario simple
            {
                message: "Debe ser un correo institucional o un usuario válido",
            }
        ),

    password: z
        .string()
        .min(1, "La contraseña es obligatoria")
        .min(6, "La contraseña debe tener al menos 6 caracteres")
});