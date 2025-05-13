import MainFooter from "../mainFooter/mainFooter";

export default function TopFooter() {
    return (
        <div className="flex flex-col gap-20 md:gap-0 justify-between h-full md:h-80 bg-[#E0E0FF] text-[#73737e] p-8">
            <div className="flex justify-between flex-col md:flex-row gap-20">
                <div className="flex flex-col gap-3 font-bold text-md md:text-2xl">
                    <span className="text-xl md:text-2xl">Universidad del Cauca</span>
                    <span className="text-sm md:text-base">NIT. 891500319-2</span>
                </div>

                <div className="flex gap-5 h-16 items-center">
                    <div className="flex flex-col gap-2 items-start md:items-end">
                        <p>Sistema de Gestión Academica</p>
                        <p>Vicerrectoría</p>
                        <p>relacionesinter@unicauca.edu.co</p>
                    </div>
                    <div className="border border-[#73737e] h-full"></div>

                    <div className="flex flex-col gap-2 text-[12px] md:text-sm">
                        <span>División de Tecnologías de la Información y las Comunicaciones - TIC</span>
                        <span>Version 1.0</span>
                        <span>2025</span>
                    </div>
                </div>
            </div>
            <MainFooter />
        </div>

    );
}
