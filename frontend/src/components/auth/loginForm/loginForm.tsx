"use client";

import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import LabeledInput from "@/components/ui/labeledInput";
import LoginHeader from "./header/header";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { userSchema } from "@/validations/userSchema";
import { toast } from "sonner";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { loadCredentials, saveCredentials, clearCredentials } from "@/lib/rememberMe";
import { authAction } from "@/actions/authAction";
import { LoginResponse } from "@/actions/responseType";
import { Loader2 } from "lucide-react";
import { isAuthenticated } from "@/lib/auth";

export default function LoginForm() {
    const navigate = useNavigate();
    const [rememberMe, setRememberMe] = useState(false);
    const [isLoading, setIsLoading] = useState(false);

    const { register, handleSubmit, formState: { errors }, setValue, setError } = useForm({
        resolver: zodResolver(userSchema),
        defaultValues: {
            username: "",
            password: ""
        },
        mode: "onSubmit"
    });

    useEffect(() => {
        if (isAuthenticated()) {
            redirectToRolePage();
        }
    }, [navigate]);

    useEffect(() => {
        const savedCredentials = loadCredentials();
        if (savedCredentials) {
            setValue("username", savedCredentials.username);
            setValue("password", savedCredentials.password);
            setRememberMe(true);
        }
    }, [setValue]);

    interface FormData {
        username: string;
        password: string;
    }

    const onSubmit = async (data: FormData) => {
        setIsLoading(true);
        try {
            const res = await authAction(data);

            if (res.success) {
                handleSuccessfulLogin(data);
                return;
            }
            handleAuthError(res);
        } catch (error) {
            console.error("Error al ejecutar Server Action:", error);
            setError("username", { type: "server", message: "" });
            setError("password", { type: "server", message: "" });
            setError("root", {
                type: "server",
                message: "Error al iniciar sesión. Por favor, inténtalo nuevamente."
            });
            toast.error("Error al iniciar sesión. Por favor, inténtalo nuevamente.");
        } finally {
            setIsLoading(false);
        }
    };

    const handleSuccessfulLogin = (data: FormData) => {
        if (rememberMe) {
            saveCredentials(data.username, data.password);
        } else {
            clearCredentials();
        }

        toast.success("¡Inicio de sesión exitoso!");
        redirectToRolePage();
    };

    const redirectToRolePage = () => {
        const rolesString = localStorage.getItem("roles");
        let roles: string[] = [];
        if (rolesString) {
            try {
                roles = JSON.parse(rolesString);
            } catch {
                roles = [];
            }
        }


        if (roles.some(role => ["ADMIN_ROLE", "TEACHER_ROLE", "COORDINATOR_ROLE"].includes(role))) {
            navigate("/rubricas");
        }
        else if (roles.includes("STUDENT_ROLE")) {
            navigate("/estudiante");
        } else {
            navigate("/");
        }
    };

    const handleAuthError = (result: LoginResponse) => {
        setError("username", { type: "server", message: "" });
        setError("password", { type: "server", message: "" });

        let errorMessage = "Error al iniciar sesión. Por favor, inténtalo nuevamente.";

        if (result.success === false) {
            errorMessage = "Contraseña incorrecta o usuario no encontrado.";
        }
        setError("root", { type: "server", message: errorMessage });
        toast.error(errorMessage);
    };

    return (
        <div className="flex flex-col items-center gap-3 w-full md:w-1/2">
            <div className="w-full p-2">
                <LoginHeader />
            </div>
            <div className="flex flex-col justify-center items-center w-full h-full py-10 md:py-0">
                <div className="flex flex-col items-start w-[70%] md:w-[50%]">
                    <h1 className="text-2xl md:text-3xl text-(--primary-regular-color) font-bold">
                        Inicio de sesión
                    </h1>
                </div>
                <form
                    className="flex flex-col gap-3 w-[70%] md:w-[50%] py-10"
                    onSubmit={handleSubmit(onSubmit)}
                >
                    <LabeledInput
                        label="Correo institucional o usuario"
                        id="username"
                        type="text"
                        placeholder="Correo institucional o usuario"
                        required={true}
                        className={errors.username ? "border-error" : ""}
                        {...register("username")}
                    />
                    {errors.username && errors.username.message !== "" && (
                        <span className="text-xs md:text-sm text-error font-medium">{errors.username.message}</span>
                    )}

                    <LabeledInput
                        label="Contraseña"
                        id="password"
                        placeholder="Contraseña"
                        type="password"
                        required={true}
                        className={errors.password ? "border-error" : ""}
                        {...register("password")}
                    />
                    {errors.password && errors.password.message !== "" && (
                        <span className="text-xs md:text-sm text-error font-medium text-red-600">{errors.password.message}</span>
                    )}
                    {errors.root && (
                        <span className="text-error text-[12px] md:text-sm text-red-600">
                            {errors.root.message as string}
                        </span>
                    )}

                    <div className="flex items-center space-x-2 pb-2">
                        <Checkbox
                            id="terms"
                            checked={rememberMe}
                            onCheckedChange={(checked) => setRememberMe(checked === true)}
                        />
                        <label
                            htmlFor="terms"
                            className="text-[12px] md:text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
                        >
                            Recordar por 30 días
                        </label>
                    </div>

                    <Button type="submit" className="w-full" disabled={isLoading}>
                        {isLoading ? (
                            <span className="flex items-center justify-center gap-2">
                                <Loader2 className="animate-spin h-4 w-4" />
                                Iniciando...
                            </span>
                        ) : (
                            "Iniciar sesión"
                        )}
                    </Button>
                </form>
            </div>
        </div>
    );
}