import { Separator } from "@/components/ui/separator"
import NavUser from "@/components/layouts/NavUser"

import {
    SidebarTrigger,
} from "@/components/ui/sidebar"


export default function Header() {
    const data = {
        user: {
            name: localStorage.getItem("fullname") || "Nombre de usuario",
            email: localStorage.getItem("email") || "Email",
            avatar: "https://github.com/Viperexz.png",
        }
    }
    return (
        <header className="flex h-16 shrink-0 items-center gap-2 shadow-md px-3">
            <div className="flex items-center gap-2">
                <SidebarTrigger />
                <Separator orientation="vertical" className="mr-2 h-4" />
                <h1 className="title4" style={{ color: "var(--primary-regular-color)" }}>Universidad del Cauca</h1>
            </div>

            <div className="header-button ml-auto flex items-center gap-2" style={{ maxWidth: "300px" }}>
                <NavUser user={data.user} />
            </div>
        </header>
    );
};