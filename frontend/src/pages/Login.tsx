import LoginForm from "@/components/auth/loginForm/loginForm";
import InfoSection from "@/components/auth/infoSection/infoSection";
import MultiColorBar from "@/components/ui/multi-color-bar";
import TopFooter from "@/components/footer/topFooter/topFooter";

export default function Login() {
    return (
        <>
            <div className="flex gap-2 w-full h-full md:h-screen">
                <LoginForm />
                <InfoSection />
            </div>
            <MultiColorBar />
            <TopFooter />
        </>
    );
}