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
            roles: ["ADMIN_ROLE", "COORDINATOR_ROLE", "TEACHER_ROLE"],
            items: [
                {
                    title: "Ver Rubricas",
                    url: "/rubricas",
                    roles: ["ADMIN_ROLE", "COORDINATOR_ROLE", "TEACHER_ROLE"],
                },
                {
                    title: "Crear",
                    url: "/rubricas/crear",
                    roles: ["ADMIN_ROLE", "COORDINATOR_ROLE", "TEACHER_ROLE"],
                },
                {
                    title: "Repositorio",
                    url: "/rubricas/repositorio",
                    roles: ["ADMIN_ROLE", "COORDINATOR_ROLE", "TEACHER_ROLE"],
                }
            ],
        },
        {
            title: "Evaluaciones",
            url: "#",
            icon: PieChart,
            roles: ["ADMIN_ROLE", "COORDINATOR_ROLE", "TEACHER_ROLE"],
            items: [
                {
                    title: "Ver Evaluaciones",
                    url: "/evaluaciones",
                    roles: ["ADMIN_ROLE", "COORDINATOR_ROLE", "TEACHER_ROLE"],
                },
                {
                    title: "Crear",
                    url: "/evaluaciones/crear",
                    roles: ["ADMIN_ROLE", "COORDINATOR_ROLE", "TEACHER_ROLE"],
                },
                {
                    title: "Estadisticas",
                    url: "/estadisticas",
                    roles: ["ADMIN_ROLE", "COORDINATOR_ROLE", "TEACHER_ROLE"],
                },
            ],
        },
        {
            title: "Estadísticas",
            url: "#",
            icon: PieChart,
            items: [
                {
                    title: "Ver Estadísticas",
                    url: "/estadisticas",
                },
                
            ],
        },
    ],
    moreOptions: [
        {
            name: "Estudiante",
            url: "/estudiante",
            icon: User,
            roles: ["ADMIN_ROLE", "STUDENT_ROLE"],
        },
        {
            name: "Resultados de Aprendizaje",
            url: "/resultados",
            icon: ClipboardList,
            roles: ["ADMIN_ROLE", "COORDINATOR_ROLE", "TEACHER_ROLE"],
        },
    ],
};

function getUserRoles() {
    try {
        const roles = JSON.parse(localStorage.getItem("roles") || "[]");
        return Array.isArray(roles) ? roles : [];
    } catch {
        return [];
    }
}

export default function AppSidebar() {
    const userRoles = getUserRoles();

    const filteredNavMain = data.navMain
        .filter(item => item.roles.some(role => userRoles.includes(role)))
        .map(item => ({
            ...item,
            items: item.items.filter(sub => sub.roles.some(role => userRoles.includes(role)))
        }))
        .filter(item => item.items.length > 0);

    const filteredMoreOptions = data.moreOptions
        .filter(opt => opt.roles.some(role => userRoles.includes(role)));

    return (
        <Sidebar>
            <SidebarContent>
                <SidebarGroup>
                    <div className="flex h-11 shrink-0 items-center justify-center gap-2 shadow-md px-3">
                        <a href="/rubricas">
                            <img src="/logos/logo.svg" alt="Logo" className="h-15" />
                        </a>
                    </div>
                    <Separator className="my-3" />
                    <SidebarGroupContent>
                        <SidebarMenu>
                            <SidebarContent>
                                {filteredNavMain.length > 0 && (
                                    <NavMain items={filteredNavMain} />
                                )}
                                <NavProjects projects={filteredMoreOptions} />
                            </SidebarContent>
                        </SidebarMenu>
                    </SidebarGroupContent>
                </SidebarGroup>
            </SidebarContent>
        </Sidebar>
    );
}