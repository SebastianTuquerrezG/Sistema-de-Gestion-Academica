import { Label } from "@/components/ui/label"

export default function LoginHeader() {
    return (
        <div className="flex items-center gap-2">
            <img
                src="/logos/u blue.png"
                alt="Logo"
                width={50}
                height={50}
                className="w-auto h-[30px] md:h-[50px]"
            />
            <div className="border border-blue h-12"></div>
            <Label className="text-xs md:text-xs">
                Oficina de Relaciones <br />Interinstitucionales <br />e Internacionales
            </Label>
        </div>
    )
}
