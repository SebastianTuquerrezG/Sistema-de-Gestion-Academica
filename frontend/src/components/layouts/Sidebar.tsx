"use client"

import { Separator } from "@/components/ui/separator"
import NavMain from "@/components/NavMain"
import NavProjects from "@/components/NavProjects"

import {
    Sidebar,
    SidebarContent,
    SidebarGroup,
    SidebarGroupContent,
    SidebarMenu,
} from "@/components/ui/sidebar"

import {
    BookOpen,
    PieChart,
    Frame,
    User,
    ClipboardList,
    FolderOpen
} from "lucide-react";

const data = {
    navMain: [
        {
            title: "Rubricas",
            url: "#",
            icon: BookOpen,
            items: [
                {
                    title: "Ver Rubricas",
                    url: "/rubricas",
                },
                {
                    title: "Crear",
                    url: "/rubricas/crear",
                },
                {
                    title: "Editar",
                    url: "/rubricas/editar",
                },
            ],
        },
        {
            title: "Evaluaciones",
            url: "#",
            icon: PieChart,
            items: [
                {
                    title: "Ver Evaluaciones",
                    url: "/evaluaciones",
                },
                {
                    title: "Crear",
                    url: "/evaluaciones/crear",
                },
            ],
        },
        {
            title: "Cursos",
            url: "#",
            icon: Frame,
            items: [
                {
                    title: "Ver Cursos",
                    url: "/cursos",
                },
                {
                    title: "Crear",
                    url: "/cursos/crear",
                },
            ],
        },
    ],
    moreOptions: [
        {
            name: "Estudiantes",
            url: "/estudiantes",
            icon: User,
        },
        {
            name: "Resultados de Aprendizaje",
            url: "/resultados",
            icon: ClipboardList,
        },
        {
            name: "Repositorios de Rubricas",
            url: "/repositorios",
            icon: FolderOpen,
        },
    ],
};

export default function AppSidebar() {
    return (
        <Sidebar>
            <SidebarContent>
                <SidebarGroup>
                    <div className="flex items-center justify-center p-4">
                        <a href="/">
                            <img src="/logo.png" alt="Logo" />
                        </a>
                    </div>
                    <Separator className="my-3" />
                    <SidebarGroupContent>
                        <SidebarMenu>
                            <SidebarContent>
                                <NavMain items={data.navMain} />
                                <NavProjects projects={data.moreOptions} />
                            </SidebarContent>
                        </SidebarMenu>
                    </SidebarGroupContent>
                </SidebarGroup>
            </SidebarContent>
        </Sidebar>
    );
};