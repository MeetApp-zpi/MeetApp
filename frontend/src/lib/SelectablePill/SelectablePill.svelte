<script lang="ts">
    let cssClass: string;
    export { cssClass as class };
    export let isSelected: boolean = false;
    export let clickCallback: () => void;

    const switchSelection = () => {
        isSelected = !isSelected;
        clickCallback();
    };
</script>

<button
    on:click={switchSelection}
    class=" border-2 rounded-full hover:opacity-90 transition ease-in-out delay-100
        focus:ring focus:ring-tea font-bold {cssClass}
        {isSelected ? 'bg-grass border-pine text-ivory' : 'text-cocoa border-gray bg-snow-drift'}"
>
    <slot />
</button>
