<script lang="ts">
    import Button from '../../lib/Button/Button.svelte';
    import Header from '../../lib/Header/Header.svelte';
    import execute from '../../lib/fetchWrapper';

    import FaCalendarAlt from 'svelte-icons/fa/FaCalendarAlt.svelte';
    import FaCalendarCheck from 'svelte-icons/fa/FaCalendarCheck.svelte';
    import FaMapMarkedAlt from 'svelte-icons/fa/FaMapMarkedAlt.svelte';
    import MdAccessTime from 'svelte-icons/md/MdAccessTime.svelte';
    import MdPeople from 'svelte-icons/md/MdPeople.svelte';
    // noinspection TypeScriptCheckImport
    import { goto } from '@roxi/routify';

    export let eventId: number;

    let data = null;
    let isEnrolled: boolean = false;

    const checkEnrolledStatus = () => {
        execute(`events/isEnrolled/${eventId}`, 'GET')
            .then((r) => r.json())
            .then((r) => (isEnrolled = r));
    };

    execute(`events/${eventId}`, 'GET').then(async (response: Response) => {
        if (response.status != 200) {
            $goto('/events');
        }

        data = await response.json();
    });

    const enroll = () => {
        execute(`events/enroll/${eventId}`, 'GET').then((_) => checkEnrolledStatus());
    };

    const unenroll = () => {
        execute(`events/unenroll/${eventId}`, 'GET').then((_) => checkEnrolledStatus());
    };

    checkEnrolledStatus();
</script>

<div class="h-screen">
    <Header />
    {#if data !== null}
        <div class="h-[calc(100%-4rem)] lg:h-[calc(100%-8rem)] overflow-auto">
            <div class="flex flex-col">
                <div class="relative m-4 self-stretch">
                    {#if data.picture !== null}
                        <img class="rounded-lg object-fill w-full" src="http://localhost:8080/{data.picture}" alt="Event poster" />
                        <div class="absolute bottom-0 left-0 right-0 px-4 py-2 opacity-40 bg-ivory">
                            <h2 class="font-bold text-xl text-cocoa">
                                {data.title}
                            </h2>
                        </div>
                    {:else}
                        <h2 class="font-bold text-xl text-pine">
                            {data.title}
                        </h2>
                    {/if}
                </div>
                <div class="text-pine bg-tea flex flex-col rounded-xl p-2 m-4 gap-y-1">
                    <div class="flex flex-row align-middle">
                        <div class="w-6 mx-2">
                            <FaMapMarkedAlt />
                        </div>
                        <div class="self-center">
                            {data.location.city.name}, {data.location.voivodeship.name}
                        </div>
                    </div>
                    <div class="flex flex-row">
                        <div class="w-6 mx-2">
                            <FaCalendarAlt />
                        </div>
                        <div class="self-center">
                            {data.startDateTime.date}
                        </div>
                        <div class="w-6 mx-2">
                            <MdAccessTime />
                        </div>
                        <div class="self-center">
                            {data.startDateTime.time}
                        </div>
                    </div>
                    <div class="flex flex-row">
                        <div class="w-6 mx-2">
                            <FaCalendarCheck />
                        </div>
                        <div class="self-center">
                            {data.endDateTime.date}
                        </div>
                        <div class="w-6 mx-2">
                            <MdAccessTime />
                        </div>
                        <div class="self-center">
                            {data.endDateTime.time}
                        </div>
                    </div>
                    <div class="flex flex-row">
                        <div class="w-6 mx-2">
                            <MdPeople />
                        </div>
                        <div class="text-orange">{data.enrolled} &nbsp;</div>
                        {data.personQuota !== null ? '/ ' + data.personQuota : ''}
                    </div>
                </div>
                <div class="m-4">
                    <h3 class="text-pine text-2xl">Opis</h3>
                    <p class="text-cocoa">{data.description}</p>
                </div>
                {#if data.schedule !== null}
                    <div class="m-4">
                        <h3 class="text-pine text-2xl">Harmonogram</h3>
                        <p class="text-cocoa">{data.schedule}</p>
                    </div>
                {/if}
            </div>
            <div class="py-8" />
        </div>
    {/if}
    {#if isEnrolled}
        <Button clickHandler={unenroll} class="font-xl fixed w-64 bottom-4 left-0 right-0 mx-auto py-2 px-10">Wypisuję się!</Button>
    {:else}
        <Button clickHandler={enroll} class="font-xl fixed w-64 bottom-4 left-0 right-0 mx-auto py-2 px-10">Zapisuję się!</Button>
    {/if}
</div>
