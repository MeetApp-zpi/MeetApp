<script lang="ts">
    let cssClass: string;
    export {cssClass as class};
</script>

<div class="border-2 rounded-full transition ease-in-out delay-100 font-bold bg-grass border-pine text-ivory
        {cssClass}">
    <slot/>
</div>
