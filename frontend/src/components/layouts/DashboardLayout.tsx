import { ReactNode } from "react";
import { SidebarProvider } from "@/components/ui/sidebar";
import { SidebarInset } from "@/components/ui/sidebar";
import Sidebar from "@/components/layouts/Sidebar";
import Header from "@/components/layouts/Header";


type DashboardLayoutProps = {
    children: ReactNode;
};

const DashboardLayout = ({ children }: DashboardLayoutProps) => {
    return (
        <SidebarProvider>
            <Sidebar />
            <SidebarInset>
                <Header />
                <div className="flex flex-1 flex-col items-center justify-center gap-4 p-4">
                    {children}
                </div>
            </SidebarInset>
        </SidebarProvider>
    );
};

export default DashboardLayout;