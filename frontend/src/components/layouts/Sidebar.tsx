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
                    title: "Repositorio",
                    url: "/rubricas/repositorio",
                }
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
                    <div className="flex h-11 shrink-0 items-center justify-center gap-2 shadow-md px-3">
                        <a href="/">
                            <img src="/logos/logo.svg" alt="Logo" className="h-15" />
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